/*
* This component is show the tab inside tabs structure, this is made generic component, it can be used anywhere in the application to show the tab
*/

import React, { Component } from "react";
import PropTypes from "prop-types";
class Tab extends Component {
  static propTypes = {
    activeTab: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    onClick: PropTypes.func.isRequired,
  };

  // onClick of tab make the tab active and perform action
  onClick = () => {
    const { label, onClick } = this.props;
    onClick(label);
  };

  // Render the tab structure
  render() {
    const {
      onClick,
      props: { activeTab, label },
    } = this;

    let className = "tab-list-item";

    if (activeTab === label) {
      className += " tab-list-active";
    }

    return (
      <li className={className} onClick={onClick}>
        {label}
      </li>
    );
  }
}

export default Tab;
