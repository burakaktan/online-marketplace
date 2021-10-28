import "./App.css";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import "semantic-ui-css/semantic.min.css";
import { ToastContainer } from "react-toastify";
import Register from "./register";
import Login from "./login";
import ProductForUsers from "./productForUsers";
import AdminMenu from "./adminMenu";
import AddProducts from "./addProducts";
import ProductForAdmin from "./productForAdmin";
import AddUsers from "./addUsers";
import SearchUsers from "./searchUsers";
import AddSeller from "./addSeller";
import SearchSeller from "./searchSeller";
import EditSeller from "./editSeller";
import EditUsers from "./editUsers";
import EditProducts from "./editProducts";

import "react-toastify/dist/ReactToastify.min.css";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact={true} path="/">
            <Register />
          </Route>
          <Route path="/login">
            <Login showRegisterLink={true}></Login>
          </Route>
          <Route path="/productForUsers">
            <ProductForUsers></ProductForUsers>
          </Route>
          <Route path="/addProducts">
            <AddProducts></AddProducts>
          </Route>
          <Route path="/editProducts">
            <EditProducts></EditProducts>
          </Route>
          <Route path="/adminMenu">
            <AdminMenu></AdminMenu>
          </Route>
          <Route path="/productForAdmin">
            <ProductForAdmin></ProductForAdmin>
          </Route>
          <Route path="/addUsers">
            <AddUsers></AddUsers>
          </Route>
          <Route path="/searchUsers">
            <SearchUsers></SearchUsers>
          </Route>
          <Route path="/addSeller">
            <AddSeller></AddSeller>
          </Route>
          <Route path="/searchSeller">
            <SearchSeller></SearchSeller>
          </Route>
          <Route path="/editSeller">
            <EditSeller></EditSeller>
          </Route>
          <Route path="/editUsers">
            <EditUsers></EditUsers>
          </Route>
          <Route path="*">404 | Not Found</Route>
        </Switch>
      </BrowserRouter>
      <ToastContainer />
    </div>
  );
}

export default App;
