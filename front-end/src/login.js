import React, { useState } from "react";
import { Link, withRouter } from "react-router-dom";
import { toast } from "react-toastify";
import { Form, Button, Container, Grid, Divider } from "semantic-ui-react";

const Login = ({ showRegisterLink, history }) => {
  const [usernamePassword, setUsernamePassword] = useState({
    username: "",
    password: "",
  });
  let username;
  let password;
  const [usernamePasswordError, setUsernamePasswordError] = useState({
    username,
    password,
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUsernamePassword({ ...usernamePassword, [name]: value });
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    const { username, password } = usernamePassword;

    if (username.length < 10) {
      setUsernamePasswordError({
        ...usernamePasswordError,
        username: "At least 10 caharacters for username",
      });
      return;
    }

    if (password.length < 3) {
      setUsernamePasswordError({
        ...usernamePasswordError,
        password: "At least 3 caharacters for password",
      });
      return;
    }
    fetch("http://localhost:8080/api/auth/signin", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
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
        localStorage.setItem("token", `${response.token}`);
        toast.success(`Congrats, you have been logged in succesfully !`);

        if (response.roles[0] === "ROLE_ADMIN") {
          setTimeout(() => {
            history.push("/adminMenu");
          }, 3000);
        } else {
          setTimeout(() => {
            history.push("/productForUsers");
          }, 3000);
        }
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };
  return (
    <Container>
      <Grid columns="equal">
        <Grid.Column></Grid.Column>
        <Grid.Column>
          <Form onSubmit={handleSubmit}>
            <Form.Field>
              <label>Username</label>
              <Form.Input
                type="email"
                name="username"
                error={usernamePasswordError.username}
                required
                value={usernamePassword.username}
                onChange={handleChange}
              ></Form.Input>
            </Form.Field>

            <Form.Field>
              <label>Password</label>
              <Form.Input
                type="password"
                name="password"
                error={usernamePasswordError.password}
                required
                value={usernamePassword.password}
                onChange={handleChange}
              ></Form.Input>
            </Form.Field>

            <Button type="submit">Submit</Button>
          </Form>
          <Divider />
          {showRegisterLink && (
            <Link to="/">Do you have an account? Please login...</Link>
          )}
        </Grid.Column>
        <Grid.Column></Grid.Column>
      </Grid>
    </Container>
  );
};

export default withRouter(Login);
