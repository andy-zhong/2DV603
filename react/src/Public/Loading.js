import React from 'react';
import Spinner from "react-bootstrap/Spinner";

class Loading extends React.Component {

    constructor(props){
        super(props);
        let $this = this;
        $this.state = {
            show: false
        };
        setInterval(function(){
            $this.setState({
                show: window.loading
            });
        }, 200);
    }

    render(){
        let style1 = {
            position: 'fixed',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            background: 'rgba(0, 0, 0, .2)'
        }
        let style2 = {
            position: 'fixed',
            zIndex: 100,
            left: 0,
            right: 0,
            margin: '150px auto'
        }
        return (
            <div style={{display:(this.state.show?'block':'none')}}>
                <div style={style1}></div>
                <Spinner animation="border" role="status" style={style2} variant="success">
                    <span className="sr-only">Loading...</span>
                </Spinner>;
            </div>
        )
    }
}

export default Loading;
