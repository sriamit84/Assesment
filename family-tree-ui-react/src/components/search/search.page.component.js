/*
*  This Component is showing the search bar taking all the countries and filtering based on the name
*/

import React, { useState, useEffect } from "react";
import SearchBar from "./search.component";

const SearchPage = (props) => {
  const [input, setInput] = useState("");
  const [countryListDefault, setCountryListDefault] = useState();
  const [countryList, setCountryList] = useState();

  // get all the countries list from rest service
  const fetchData = async () => {
    return await fetch("https://restcountries.eu/rest/v2/all")
      .then((response) => response.json())
      .then((data) => {
        setCountryList(data);
        setCountryListDefault(data);
      });
  };

  // update the input country name and get the filtered list of countries based on name
  const updateInput = async (input) => {
    console.log(input);

    const filtered = countryListDefault.filter((country) => {
      return country.name.toLowerCase().includes(input.toLowerCase());
    });
    setInput(input);
    setCountryList(filtered);
    console.log(filtered);
  };

  // get the list of countries on component load
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
