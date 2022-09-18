import React, { Component } from "react";

class Timer extends Component {
  constructor(props) {
    super(props);

    this.state = {
      date: new Date(),
    };
  }

  tick() {
    this.setState({ date: new Date() });
  }

  componentDidMount() {
    this.timeId = setInterval(() => this.tick(), 1000);
    console.log("Time Did Mount");
  }

  componentWillUnmount() {
    clearInterval(this.timeId);
    console.log("Time will UnMount");
  }

  render() {
    return (
      <div>
        <p
          className="nav-link"
          style={{
            color: "	#121212",
            fontSize: "16px",
            fontWeight: "600",
            paddingTop: "20px",
          }}
        >
          Date: {this.state.date.toLocaleDateString()} &nbsp; Time:{" "}
          {this.state.date.toLocaleTimeString()} &nbsp; &nbsp; &nbsp;&#160;
        </p>
      </div>
    );
  }
}

export default Timer;
