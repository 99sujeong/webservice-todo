import React from "react";
import Todo from "./Todo";
import AddTodo from "./AddTodo";
import { Paper, List, Container } from "@material-ui/core";
import "./App.css";
import { call } from "./service/ApiService";

class App extends React.Component {
  constructor(props) {
    super(props);
    // 1. item -> item array
    this.state = {
      items: [],
    };
  }

  componentDidMount() {
    call("/todo", "GET", null).then((response) =>
      this.setState({ items: response.data })
    );
  }

  add = (item) => {
    // const thisItems = this.state.items;
    // item.id = "ID-" + thisItems.length; // key를 위한 id 추가
    // item.done = false; // done 초기화
    // thisItems.push(item); // 리스트에 아이템 추가
    // this.setState({ items: thisItems }); // 업데이트는 반드시 this.setState로 해야 됨
    // console.log("items : ", this.state.items);
    call("/todo", "POST", item).then((response) =>
      this.setState({ items: response.data })
    );
  };

  delete = (item) => {
    const thisItems = this.state.items;
    console.log("Before Update Items : ", this.state.items);
    const newItems = thisItems.filter((e) => e.id !== item.id);
    this.setState({ items: newItems }, () => {
      // debugging callback
      console.log("Update Items: ", this.state.items);
    });
    // call("/todo", "DELETE", item).then((response) =>
    //   this.setState({ items: response.data })
    // );
  };

  update = (item) => {
    call("/todo", "PUT", item).then((response) =>
      this.setState({ items: response.data })
    );
  };

  render() {
    // 2. js가 제공하는 map 함수를 이용해 배열 반복해 <Todo ... /> component 생성
    var todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }}>
        <List>
          {this.state.items.map((item, idx) => (
            // delete, update 함수 연결
            <Todo
              item={item}
              key={item.id}
              delete={this.delete}
              update={this.update}
            />
          ))}
        </List>
      </Paper>
    );
    return (
      <div className="App">
        <Container maxWidth="md">
          <AddTodo add={this.add} />
          <div className="TodoList">{todoItems}</div>
        </Container>
      </div>
    );
  }
}

export default App;
