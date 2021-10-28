import React from "react";
import { toast } from "react-toastify";
import { Form, Button, Container, Grid } from "semantic-ui-react";

class AddProducts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      price: 0.0,
      stok: 0,
      sellerName: "",
    };
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { name, price, stok, sellerName } = this.state;
    const token = window.localStorage.getItem("token");
    fetch("http://localhost:8080/api/user/addProduct", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({ name, price, stok, sellerName }),
    })
      .then((r) => {
        if (r.ok) {
          return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
          return Promise.reject(new Error("An error has occured"));
        }
        return Promise.reject(new Error("An unknown error has occured"));
      })
      .then((r) => r.json())
      .then((response) => {
        toast.success(`${response.message}`);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  handleChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };
  render() {
    return (
      <Container>
        <Grid columns="equal">
          <Grid.Column></Grid.Column>
          <Grid.Column>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <label>Name</label>
                <Form.Input
                  type="text"
                  name="name"
                  required
                  value={this.state.name}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <label>Price</label>
                <Form.Input
                  type="number"
                  name="price"
                  required
                  value={this.state.price}
                  onChange={(event) => {
                    this.handleChange(event);
                  }}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <label>Stok</label>
                <Form.Input
                  type="number"
                  name="stok"
                  required
                  value={this.state.stok}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <label>Seller Name</label>
                <Form.Input
                  type="text"
                  name="sellerName"
                  required
                  value={this.state.sellerName}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Button type="submit">Ekle!</Button>
            </Form>
          </Grid.Column>
          <Grid.Column></Grid.Column>
        </Grid>
      </Container>
    );
  }
}
export default AddProducts;
