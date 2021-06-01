import React from "react";

// This component is common SearchBar component

const SearchBar = ({ keyword, setKeyword }) => {
  return (
    <form>
      <div className="col-75">
        <input
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
