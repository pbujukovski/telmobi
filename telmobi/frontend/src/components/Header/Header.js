import React from 'react';
import {Link, withRouter} from 'react-router-dom'
import "./Header.css"
const Header = (props) => {
    const handleLogout = () => {
        props.history.push('/');
        props.onLogout();
    };
    const manufacturers=props.manufacturerNames.map((manufacturer,index)=>{
        return (<Link to={"/manufacturer/"+manufacturer} key={index} className="nav-link text-dark">{manufacturer.toString().charAt(0).toUpperCase()+manufacturer.toString().slice(1)}</Link>);
    });
    let menuItems=[]
    
        if(props.currentUser){
            menuItems= [
              
            <ul className="navbar-nav ml-auto">
            <li className="nav-item dropdown" >
            <span className="nav-link dropdown-toggle d-block mr-5 text-brown" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            {props.currentUser.username}
            </span>
        <div className="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
          <Link to={"#"} className="nav-link text-dark" onClick={handleLogout}>Logout</Link>
        </div>
      </li>
    </ul>
            ];
        }
        else{ menuItems=[
            <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                    <Link to={"/signin"} className="nav-link text-brown">Login</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/signup"} className="nav-link text-brown">Register</Link>
                </li>
                </ul>
        ];}
        




    return (
<nav className="navbar navbar-expand-lg app-navigation">
  <Link to={"/"} className="navbar-brand text-brown">Telmobi</Link>
 {props.currentUser!==null && props.currentUser.roleName === "ROLE_ADMIN"?<span className="nav-link text-brown">
            <Link to={"/phones/create"} className="text-brown" ><b>Add Phone</b></Link>
            </span>:""}
  
  <button className="navbar-toggler" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span className="navbar-toggler-icon"></span>
  </button>

  <div className="collapse navbar-collapse" id="navbarSupportedContent">
    <ul className="navbar-nav mr-auto">
      <li className="nav-item dropdown" >
            <span className="nav-link dropdown-toggle d-block text-brown" href="#" id="manufacturerDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Manufacturer
            </span>
        <div className="dropdown-menu" aria-labelledby="manufacturerDropdown">
          {manufacturers}

        </div>

      </li>
    </ul>
        {menuItems}
        </div>
        </nav>
    );
};

export default withRouter(Header);