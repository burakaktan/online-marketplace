import React from "react";
import { toast } from "react-toastify";
import { Form, Button, Container, Grid } from "semantic-ui-react";

class EditProducts extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      oldName: "",
      name: "",
      price : 0,
      stok: 0,

    };
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { oldName , name , price , stok} = this.state;
    const token = window.localStorage.getItem("token");
    fetch(
      "http://localhost:8080/api/user/editProduct?" +
        new URLSearchParams({ oldName: oldName }),
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ name, price, stok}),
      }
    )
      .then((r) => {
        if (r.ok) {
          return r;
        }
        if (r.status === 401 || r.status === 403 || r.status === 500) {
          return Promise.reject(new Error("bir hata oluştu"));
        }
        return Promise.reject(new Error("bilinmeyen bir hata oluştu"));
      })
      .then((r) => r.json())
      .then((response) => {
        toast.success(`Product edited`);
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
                <label>Old Product Name</label>
                <Form.Input
                  type="text"
                  name="oldName"
                  required
                  value={this.state.oldName}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>
              <Form.Field>
                <label>New Product Name</label>
                <Form.Input
                  type="text"
                  name="name"
                  required
                  value={this.state.name}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>
               <Form.Field>
                <label>New Price</label>
                <Form.Input
                  type="number"
                  name="price"
                  required
                  value={this.state.price}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>
               <Form.Field>
                <label>New Stok</label>
                <Form.Input
                  type="number"
                  name="stok"
                  required
                  value={this.state.stok}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Button type="submit">Submit</Button>
            </Form>
          </Grid.Column>
          <Grid.Column></Grid.Column>
        </Grid>
      </Container>
    );
  }
}
export default EditProducts;
