import React, { useState, useEffect } from "react";
import SearchBar from "./search.component";

const SearchPage = (props) => {
  const [input, setInput] = useState("");
  const [countryListDefault, setCountryListDefault] = useState();
  const [countryList, setCountryList] = useState();

  const fetchData = async () => {
    return await fetch("https://restcountries.eu/rest/v2/all")
      .then((response) => response.json())
      .then((data) => {
        setCountryList(data);
        setCountryListDefault(data);
      });
  };

  const updateInput = async (input) => {
    console.log(input);

    const filtered = countryListDefault.filter((country) => {
      return country.name.toLowerCase().includes(input.toLowerCase());
    });
    setInput(input);
    setCountryList(filtered);
    console.log(filtered);
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <>
      <SearchBar input={input} setKeyword={updateInput} />
    </>
  );
};

export default SearchPage;
