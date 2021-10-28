import React from "react";
import { Link } from "react-router-dom";
import { Container } from "semantic-ui-react";

class AdminMenu extends React.Component {
  render() {
    return (
      <Container>
        <Link to="/productForAdmin">Search-remove products</Link>
        <br></br>
        <Link to="/addProducts">Add products</Link>
        <br></br>
        <Link to="/editProducts">Edit products (name - price - stok)</Link>
        <br></br>
        <Link to="/searchUsers">Search - remove users</Link>
        <br></br>
        <Link to="/addUsers">Add users</Link>
        <br></br>
        <Link to="/editUsers">Edit users(name)</Link>
        <br></br>
        <Link to="/searchSeller">Search-remove sellers</Link>
        <br></br>
        <Link to="/addSeller">Add sellers</Link>
        <br></br>
        <Link to="/editSeller">Edit sellers (name)</Link>
      </Container>
    );
  }
}
export default AdminMenu;
