import { Admin, fetchUtils, Resource, withLifecycleCallbacks } from 'react-admin';
import simpleRestProvider from "ra-data-simple-rest";
import ProductList from './ProductList';
import EditProduct from './ProductEdit';
import CreateProduct from './CreateProduct';
import CategoryList from './Category/CategoryList';
import CategoryEdit from './Category/CategoryEdit';
import { fileUploadAPI } from '../../api/fileUpload';

const httpClient = (url, options = {}) => {
  const token = localStorage.getItem('authToken');
  if (!options.headers) options.headers = new Headers();
  options.headers.set('Authorization', `Bearer ${token}`);
  return fetchUtils.fetchJson(url, options);
}

// Function to upload file to Cloudinary with unique public_id
const uploadToCloudinary = async (file, publicId = null) => {
  const formData = new FormData();
  formData.append("image", file);
  
  // Add public_id if provided to avoid duplicates
  if (publicId) {
    formData.append("public_id", publicId);
  }
  
  try {
    const data = await fileUploadAPI(formData);
    return data;
  } catch (error) {
    console.error("Upload error:", error);
    throw new Error('Upload failed');
  }
};

// Cache to store uploaded files in current session
const uploadCache = new Map();

const dataProvider = withLifecycleCallbacks(simpleRestProvider('http://localhost:8080/api', httpClient), [
  {
    resource: "products",
    beforeSave: async (params, dataProvider) => {
      console.log("Params ", params);
      let requestBody = {
        ...params
      };
      
      let productResList = params?.productResources ?? [];

      // Upload thumbnail if exists and is a new file (has rawFile)
      if (params?.thumbnail?.rawFile) {
        try {
          const file = params.thumbnail.rawFile;
          const fileKey = `${file.name}_${file.size}_${file.lastModified}`;
          
          // Check cache first
          if (uploadCache.has(fileKey)) {
            requestBody.thumbnail = uploadCache.get(fileKey);
            console.log("Using cached thumbnail:", requestBody.thumbnail);
          } else {
            // Create unique public_id from file info
            const publicId = `products/thumbnail_${Date.now()}_${file.name.replace(/\.[^/.]+$/, "")}`;
            const thumbnailResponse = await uploadToCloudinary(file, publicId);
            requestBody.thumbnail = thumbnailResponse.secure_url;
            
            // Cache the result
            uploadCache.set(fileKey, requestBody.thumbnail);
            console.log("Thumbnail uploaded:", thumbnailResponse.secure_url);
          }
        } catch (error) {
          console.error("Thumbnail upload failed:", error);
          throw new Error("Failed to upload thumbnail");
        }
      } else if (params?.thumbnail && typeof params.thumbnail === 'string') {
        // Keep existing thumbnail URL if it's already a string (already uploaded)
        requestBody.thumbnail = params.thumbnail;
      }

      // Upload product resources if exist and are new files
      if (productResList.length > 0) {
        try {
          const newProductResList = await Promise.all(
            productResList.map(async (productResource, index) => {
              // Only upload if it's a new file (has rawFile)
              if (productResource?.url?.rawFile) {
                const file = productResource.url.rawFile;
                const fileKey = `${file.name}_${file.size}_${file.lastModified}`;
                
                // Check cache first
                if (uploadCache.has(fileKey)) {
                  return {
                    ...productResource,
                    url: uploadCache.get(fileKey),
                  };
                } else {
                  // Create unique public_id from file info
                  const publicId = `products/resource_${Date.now()}_${index}_${file.name.replace(/\.[^/.]+$/, "")}`;
                  const fileUploadRes = await uploadToCloudinary(file, publicId);
                  
                  // Cache the result
                  uploadCache.set(fileKey, fileUploadRes.secure_url);
                  
                  return {
                    ...productResource,
                    url: fileUploadRes.secure_url,
                  };
                }
              } else if (productResource?.url && typeof productResource.url === 'string') {
                // Keep existing URL if it's already a string (already uploaded)
                return productResource;
              }
              return productResource;
            })
          );
          
          requestBody.productResources = newProductResList;
          console.log("Product resources processed successfully");
        } catch (error) {
          console.error("Product resources upload failed:", error);
          throw new Error("Failed to upload product resources");
        }
      }

      const request = {
        ...requestBody,
      };
      
      console.log("Request Body ", request);
      return request;
    }
  }
]);

export const AdminPanel = () => {
  return (
    <Admin dataProvider={dataProvider} basename='/admin'>
      <Resource name='products' list={ProductList} edit={EditProduct} create={CreateProduct} />
      <Resource name='category' list={CategoryList} edit={CategoryEdit} />
    </Admin>
  );
};