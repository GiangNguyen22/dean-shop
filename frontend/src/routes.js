import { createBrowserRouter } from "react-router-dom";
import ProductListPage from "./pages/ProductListPage/ProductListPage";
import ShopApplicationWrapper from "./pages/ShopApplicationWrapper";
import App from "./App";
import ProductDetails from "./pages/ProductDetailPage/ProductDetails";
import { loadProductBySlug } from "./routes/product";
import AuthenticationWrapper from "./pages/AuthenticationWrapper";
import Register from "./pages/Register/Register";
import Login from "./pages/Login/Login";
import OAuth2Callback from "./pages/OAuth2Callback";
import Cart from "./pages/Cart/Cart";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import Account from "./pages/Account/Account";
import Checkout from "./pages/Checkout/Checkout";
import ConfirmPayment from "./pages/ComfirmPayment/ComfirmPayment";
import OrderConfirmed from "./pages/OrderComfirmed/OrderComfirmed";
import Profile from "./pages/Account/Profile";
import Orders from "./pages/Account/Orders";
import Settings from "./pages/Account/Settings";
import {AdminPanel} from "./pages/AdminPanel/AdminPanel";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <ShopApplicationWrapper />,
    children: [
      {
        path: "/",
        element: <App />
      },
      {
        path: "/women",
        element: <ProductListPage categoryType='WOMEN' />,
      },
      {
        path: "/men",
        element: <ProductListPage categoryType='MEN' />,
      },
      {
        path: "/kids",
        element: <ProductListPage categoryType='KIDS' />,
      },
      {
        path: "/product/:slug",
        loader: loadProductBySlug,
        element: <ProductDetails />
      },
      {
        path: "/cart-items",
        element: <Cart/>
      },
      {
        path: "/account-details/",
        element: <ProtectedRoute><Account/></ProtectedRoute>,
        children: [
          {
            path: "profile",
            element: <ProtectedRoute><Profile/></ProtectedRoute>
          },
           {
            path: "orders",
            element: <ProtectedRoute><Orders/></ProtectedRoute>
          },
           {
            path: "settings",
            element: <ProtectedRoute><Settings/></ProtectedRoute>
          }
        ]
      }, 
      {
        path: "/checkout",
        element: <ProtectedRoute><Checkout/></ProtectedRoute>
      },
       {
          path:'/orderConfirmed',
          element: <OrderConfirmed />
         }
    
    ]
  },
  {
    path: "/v1/",
    element: <AuthenticationWrapper />,
    children: [
      {
        path: "login",
        element: <Login />
      },
      {
        path: "register",
        element: <Register />
      }
    ]
  },

  {
    path: "/oauth2/callback",
    element: <OAuth2Callback/>
  },
  {
     path:'/confirmPayment',
     element:<ConfirmPayment />
    },
    {
      path:'/admin/*',
      element:<ProtectedRoute><AdminPanel /></ProtectedRoute>
    }

]);
