import React from "react";
import { toast } from "react-toastify";
import { Checkbox, Form, Button, Container, Grid } from "semantic-ui-react";

// This page is similar to sign up page
// There is no meaning for using here instead of sign up page

class AddUsers extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      passwordRepeat: "",
      admin: false,
    };
  }

  handleSubmit = (event) => {
    event.preventDefault();
    const { username, password, passwordRepeat, admin } = this.state;

    if (username.length < 10) {
      this.setState({ usernameError: "At least 10 caharacters for username" });
      return;
    }

    if (password.length < 3) {
      this.setState({ passwordError: "At least 3 caharacters for password" });
      return;
    }

    if (password !== passwordRepeat) {
      this.setState({
        passwordError: "Password and passwordRepeat should match",
        passwordRepeatError: "Password and passwordRepeat should match",
      });
      return;
    } else {
      this.setState({
        passwordError: null,
        passwordRepeatError: null,
      });
    }

    fetch("http://localhost:8080/api/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password, admin }),
    })
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
        toast.success(`User added`);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  handleChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  adminButtonClicked = (event) => {
    const newAdminValue = !this.state.admin;
    setTimeout(() => {
      this.setState({ admin: newAdminValue });
      toast.info(`new admin value = ${this.state.admin}`);
    }, 300);
  };

  render() {
    return (
      <Container>
        "This page is similar to sign up page, don't use here"
        <Grid columns="equal">
          <Grid.Column></Grid.Column>
          <Grid.Column>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <label>Username</label>
                <Form.Input
                  type="email"
                  name="username"
                  error={this.state.usernameError}
                  required
                  value={this.state.username}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <label>Password</label>
                <Form.Input
                  type="password"
                  name="password"
                  error={this.state.passwordError}
                  required
                  value={this.state.password}
                  onChange={(event) => {
                    this.handleChange(event);
                  }}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <label>Repeat Password</label>
                <Form.Input
                  type="password"
                  name="passwordRepeat"
                  error={this.state.passwordRepeatError}
                  required
                  value={this.state.passwordRepeat}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>

              <Form.Field>
                <Checkbox
                  onChange={this.adminButtonClicked}
                  label="I am admin"
                />
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
export default AddUsers;
