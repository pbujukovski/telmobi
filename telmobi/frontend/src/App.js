import React, { Component } from 'react';
import {BrowserRouter as Router, Route, Redirect} from 'react-router-dom'
import Auth from "./service/authService"
import {ACCESS_TOKEN} from "./constants/index"
import {toast, ToastContainer, ToastPosition} from 'react-toastify'
import Header from "./components/Header/Header"
import bg2 from "./Images/bg2.jpg"
import Login from "./components/User/UserLogin/Login"
import Register from "./components/User/UserRegister/Register"
import Profile from "./components/User/UserProfile/Profile"
import PrivateRoute from "./components/common/PrivateRoute/PrivateRoute"
import LoadingIndicator from "./components/common/LoadingIndicator/LoadingIndicator"
import MS from "./service/manufacturerService"
import phoneAdd from "./components/Phone/PhoneAdd/PhoneAdd"
import Phones from "./components/Phone/Phones/Phones"
import PhoneCardCarousel from "./components/Phone/PhoneCardCarousel/PhoneCardCarousel"
import PhoneDetails from "./components/Phone/PhoneDetails/PhoneDetails"

class App extends Component {
  
  constructor(props){
    toast.configure({position:ToastPosition.TOP_CENTER,autoClose:3000});
    super(props);
    this.state={
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      manufacturers:[]
    };
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
    this.loadManufacturerNames=this.loadManufacturerNames.bind(this);
  }
  loadCurrentUser = () => {
    this.setState({
      isLoading: true
    });
    Auth.getCurrentUser()
        .then(response => {
            this.setState({
                currentUser: response.data,
                isAuthenticated: true,
                isLoading:false
            });
        }).catch(error => {
          this.setState({
            isLoading: false
          });
    });
  };
  handleLogin = (request) => {
    toast.success("You're successfully logged in.")
    this.loadCurrentUser();
  };
  handleLogout = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    toast.success("You've successfully logged out.")
    this.setState({
        currentUser: null,
        isAuthenticated: false
    });
  };
  loadManufacturerNames=()=>{
      MS.getAll().then((data)=>{
        this.setState({
          manufacturers:data.data
        });
      }).catch(error=>{
          console.log(error);
      });
  }
  componentDidMount(){
    this.loadManufacturerNames();
    this.loadCurrentUser();

   
  }
  render() {
    if(this.state.isLoading){
      return <LoadingIndicator/>
    }
    let logiranje=[];
    if(!this.state.isAuthenticated)
      logiranje=<Login onLogin={this.handleLogin} getUser={this.loadCurrentUser}/>
    else
      logiranje=<Redirect to="/"/>
    return (
      <Router>
        <div className="App" style={  {minHeight:'100vh', width:'100%',padding:'0',background: "url("+bg2+")",
          backgroundPosition: 'center center',backgroundSize: 'cover',backgroundAttachment: 'fixed'}}>
          <Header manufacturerNames={this.state.manufacturers} isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} onLogout={this.handleLogout}/>
          <div className="container">
            <Route path={"/signin"} exact>
              {logiranje}
            </Route>
            <Route path={"/signup"} exact>
              <Register></Register>
            </Route>
            <Route path={"/"} exact ><Phones authenticated={this.state.isAuthenticated} listTypeBy=""/></Route>
            <Route path={"/phone/:phoneID"} user={this.state.currentUser} authenticated={this.state.isAuthenticated} render={(props)=><PhoneDetails user={this.state.currentUser}/>}/>
            <Route path={"/manufacturer/:manufacturerName"} user={this.state.currentUser} render={(props)=><Phones authenticated={this.state.isAuthenticated} listTypeBy="manufacturer" {...props} />} />
            <PrivateRoute path={"/phones/create"} authenticated ={this.state.isAuthenticated} component={phoneAdd} exact ></PrivateRoute>
            <PrivateRoute exact path="/users/:username" jwtRefresh={this.handleLogout} authenticated={this.state.isAuthenticated} component={Profile}/>
          </div>
        </div>
        </Router>
    );
  }
}

export default App;
