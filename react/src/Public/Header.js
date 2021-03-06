import React from "react";

import './Header.css';

class Header extends React.Component {

    constructor(props){
        super(props);
        this.state = {

        }
    }

    render(){
        return (
            <div className="header">
                <div className="left">{this.props.title2}</div>
                <div className="right">
                    <span className="fa fa-home"></span>
                    {this.props.title1==null?'':' / '+this.props.title1}
                    {this.props.title2==null?'':' / '+this.props.title2}
                </div>
            </div>
        )
    }
}

export default Header;
