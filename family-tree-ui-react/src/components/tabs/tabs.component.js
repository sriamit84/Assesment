/*
*  This component is to show the tabs on UI which will intern have tab component, This is to make the tab structure generic
*/

import React, { Component } from "react";
import PropTypes from "prop-types";
import Tab from "./tab.component";
class Tabs extends Component {
  static propTypes = {
    children: PropTypes.instanceOf(Array).isRequired,
  };

  // inside the constructor update the default tab to first one
  constructor(props) {
    super(props);

    this.state = {
      activeTab: this.props.children[0].props.label,
    };
  }

  // set the tab as active which was clicked
  onClickTabItem = (tab) => {
    this.setState({ activeTab: tab });
  };

  // render the tabs structure, children is the list of tabs which need to be shown
  render() {
    const {
      onClickTabItem,
      props: { children },
      state: { activeTab },
    } = this;

    return (
      <div className="tabs">
        <ol className="tab-list">
          {children.map((child) => {
            const { label } = child.props;

            return (
              <Tab
                activeTab={activeTab}
                key={label}
                label={label}
                onClick={onClickTabItem}
              />
            );
          })}
        </ol>
        <div className="tab-content">
          {children.map((child) => {
            if (child.props.label !== activeTab) return undefined;
            return child.props.children;
          })}
        </div>
      </div>
    );
  }
}

export default Tabs;
