import { createBrowserRouter } from "react-router-dom";
import ProductListPage from "./pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./pages/ShopApplicationWrapper";
import App from "./App";
import ProductDetails from "./pages/ProductDetailPage/ProductDetails";
import { loadProductById } from "./routes/product";

export const router = createBrowserRouter([
    {
      path: "/",
      element: <ShopApplicationWrapper/>,
      children:[
        {
            path:"/",
            element:<App/>
        },
        {
            path: "/women",
            element: <ProductListPage categoryType='WOMEN'/>,
        },
        {
            path: "/men",
            element: <ProductListPage categoryType='MEN'/>,
        },
        {
            path: "/kids",
            element: <ProductListPage categoryType='KIDS'/>,
        },
        {
          path: "/product/:productId",
          loader: loadProductById,
          element: <ProductDetails/>
        }
      ]
    }

  ]);
  