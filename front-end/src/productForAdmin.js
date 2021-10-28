import React from "react";
import { toast } from "react-toastify";
import {
  Menu,
  Icon,
  Label,
  Table,
  Form,
  Button,
  Container,
  Grid,
  Divider,
} from "semantic-ui-react";

class ProductForAdmin extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      productName: "",
      products: [],
      currentPage: 0,
      pageSize: 3,
    };
  }

  componentDidMount = () => {
    this.doQuery("");
  };
  handleSubmit = (event) => {
    event.preventDefault();
    const { productName } = this.state;
    this.doQuery(productName);
  };

  doQuery = (productName) => {
    const token = window.localStorage.getItem("token");
    fetch(
      "http://localhost:8080/api/user/searchProductAdmin?" +
        new URLSearchParams({
          name: productName,
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
        this.setState({ products: response });
        toast.success(
          `${response.numberOfElements} products fetched succesfully`
        );
      })
      .catch((e) => {
        toast.error(e.message);
      });
  };

  handleChange = (event) => {
    const { name, value } = event.target;
    this.setState({ [name]: value });
  };

  removeProduct = (nameOfProduct) => {
    const token = window.localStorage.getItem("token");
    toast.info(`${nameOfProduct}`);
    fetch(
      "http://localhost:8080/api/user/deleteProduct?" +
        new URLSearchParams({ productName: nameOfProduct }),
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
      this.doQuery(this.state.productName);
    }, 1000);
  };

    changePageTo = (pageNo) => {
    this.setState({ currentPage: pageNo });
    setTimeout(() => {
      this.doQuery(this.state.productName);
    }, 500);
  };

  render() {
    const { products } = this.state;
    const pageArray = [...Array(products.totalPages).keys()];
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
                  name="productName"
                  value={this.state.productName}
                  onChange={this.handleChange}
                ></Form.Input>
              </Form.Field>
              <Button type="submit">Search!</Button>
            </Form>
            <Divider />
            {products && products.content && products.content.length > 0 ? (
              <Table celled>
                <Table.Header>
                  <Table.Row>
                    <Table.HeaderCell>Index</Table.HeaderCell>
                    <Table.HeaderCell>Name</Table.HeaderCell>
                    <Table.HeaderCell>Price</Table.HeaderCell>
                    <Table.HeaderCell>Stok</Table.HeaderCell>
                    <Table.HeaderCell>Seller Name</Table.HeaderCell>
                    <Table.HeaderCell>Remove Product</Table.HeaderCell>
                  </Table.Row>
                </Table.Header>
                <Table.Body>
                  {products.content.map((value, index) => {
                    return (
                      <Table.Row>
                        <Table.Cell>
                          <Label ribbon>
                            {this.state.pageSize * this.state.currentPage +
                              index +
                              1}
                          </Label>
                        </Table.Cell>
                        <Table.Cell>{value.name}</Table.Cell>
                        <Table.Cell>{value.price}</Table.Cell>
                        <Table.Cell>{value.stok}</Table.Cell>
                        <Table.Cell>{value.seller.name}</Table.Cell>
                        <Table.Cell>
                          <button
                            class="ui button"
                            onClick={() => {
                              this.removeProduct(value.name);
                            }}
                          >
                            Remove product
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
                          disabled={products.first}
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
                              active={products.number === value}
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
                          disabled={products.last}
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
export default ProductForAdmin;
