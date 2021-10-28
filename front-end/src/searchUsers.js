import React from "react";
import { toast } from "react-toastify";
import {
  Table,
  Divider,
  Label,
  Menu,
  Icon,
  Form,
  Button,
  Container,
  Grid,
} from "semantic-ui-react";

class SearchUsers extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      searchName: "",
      users: [],
      currentPage: 0,
      pageSize: 3,
    };
  }

  componentDidMount = () => {
    this.doQuery("");
  };
  handleSubmit = (event) => {
    event.preventDefault();
    const { searchName } = this.state;
    this.doQuery(searchName);
  };

  doQuery = (username) => {
    const token = window.localStorage.getItem("token");
    fetch(
      "http://localhost:8080/api/user/searchUsers?" +
        new URLSearchParams({
          username: username,
          no: this.state.currentPage,
          size: this.state.pageSize,
        }),
      {
        method: "GET",
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
        this.setState({ users: response });
        toast.success(`${response.numberOfElements} users fetched succesfully`);
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  handleChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  removeUser = (username) => {
    const token = window.localStorage.getItem("token");
    fetch(
      "http://localhost:8080/api/user/deleteUser?" +
        new URLSearchParams({ username: username }),
      {
        method: "DELETE",
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
        toast.success(`Removed succesfully`);
      })
      .catch((e) => {
        toast.error(e.message);
      });

    setTimeout(() => {
      this.doQuery(this.state.searchName);
    }, 500);
  };

    changePageTo = (pageNo) => {
    this.setState({ currentPage: pageNo });
    setTimeout(() => {
      this.doQuery(this.state.searchName);
    }, 500);
  };

  render() {
    const { users } = this.state;
    const pageArray = [...Array(users.totalPages).keys()];
    return (
      <Container>
        <Grid columns="equal">
          <Grid.Column></Grid.Column>
          <Grid.Column>
            <Form onSubmit={this.handleSubmit}>
              <Form.Field>
                <label>
                  Value to search (Nothing for searching everything)
                </label>
                <Form.Input
                  type="text"
                  name="searchName"
                  value={this.state.searchName}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>
              <Button type="submit">Search!</Button>
            </Form>
            <Divider />
            {users && users.content && users.content.length > 0 ? (
              <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Index</Table.HeaderCell>
                    <Table.HeaderCell>Username</Table.HeaderCell>
                    <Table.HeaderCell>Remove User</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {users.content.map((value, index) => {
                    return (
                      <Table.Row>
                        <Table.Cell>
                          <Label ribbon>
                            {this.state.pageSize * this.state.currentPage +
                              index +
                              1}
                          </Label>
                        </Table.Cell>
                        <Table.Cell>{value.username}</Table.Cell>
                        <Table.Cell>
                          <button
                            class="ui button"
                            onClick={() => {
                              this.removeUser(value.username);
                            }}
                          >
                            Remove user
                          </button>
                        </Table.Cell>
                      </Table.Row>
                    );
                  })}
                </Table.Body>

                <Table.Footer>
                  <Table.Row>
                    <Table.HeaderCell colSpan="4">
                      <Menu floated="right" pagination>
                        <Menu.Item
                          onClick={() => {
                            this.changePageTo(this.state.currentPage - 1);
                          }}
                          as="a"
                          disabled={users.first}
                          icon
                        >
                          <Icon name="chevron left" />
                        </Menu.Item>
                        {pageArray.map((value, index) => {
                          return (
                            <Menu.Item
                              onClick={() => {
                                this.changePageTo(index);
                              }}
                              active={users.number === value}
                              as="a"
                            >
                              {value + 1}
                            </Menu.Item>
                          );
                        })}
                        <Menu.Item
                          onClick={() => {
                            this.changePageTo(this.state.currentPage + 1);
                          }}
                          as="a"
                          disabled={users.last}
                          icon
                        >
                          <Icon name="chevron right" />
                        </Menu.Item>
                      </Menu>
                    </Table.HeaderCell>
                  </Table.Row>
                </Table.Footer>
              </Table>
            ) : (
              "Couldn't find content!"
            )}
          </Grid.Column>
          <Grid.Column></Grid.Column>
        </Grid>
      </Container>
    );
  }
}
export default SearchUsers;
