import React from "react";

const SearchBar = ({ keyword, setKeyword }) => {
  //   const BarStyling = {
  //     width: "20rem",
  //     background: "#F2F1F9",
  //     border: "none",
  //     padding: "0.5rem",
  //   };
  return (
    <form>
      <div className="col-75">
        <input
          //style={BarStyling}
          type="text"
          key="random1"
          value={keyword}
          placeholder={"search person"}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <input type="button" value="Search" />
      </div>
    </form>
  );
};

export default SearchBar;
