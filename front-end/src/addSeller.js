import React from "react";
import { toast } from "react-toastify";
import { Form, Button, Container, Grid } from "semantic-ui-react";

class AddSeller extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
    };
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { name } = this.state;
    const token = window.localStorage.getItem("token");
    fetch(
      "http://localhost:8080/api/user/addSeller?" +
        new URLSearchParams({ name: name }),
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
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
        toast.success(`Seller added`);
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
                <label>Seller Name</label>
                <Form.Input
                  type="text"
                  name="name"
                  required
                  value={this.state.name}
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
export default AddSeller;
