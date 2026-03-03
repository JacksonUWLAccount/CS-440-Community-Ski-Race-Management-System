function signup(username,email,password,firstName,lastName){

    // let username = "mattdvora";
    // let email = "mdvor4@gmail.com";
    // let password = "Password";
    // let firstName = "Ean";
    // let lastName = "Mares";

    fetch("http://localhost:8080/api/signup", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        username: username,
        email: email,
        password: password,
        firstName: firstName,
        lastName: lastName
    })
    })
    .then(res => res.json())
    .then(success => { console.log(success);});
}

function login(username, password){

    username = "mattdvora";
    password = "Password";

    fetch("http://localhost:8080/api/login", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({
        username: username,
        password: password,
    })
    })
    .then(res => res.json())
    .then(success => { console.log(success);});
}


document.addEventListener("DOMContentLoaded",function(){
    document.getElementById("signup").addEventListener("click", function(){
        let username = document.getElementById("usernameIn").value;
        let email = document.getElementById("emailIn").value;
        let password = document.getElementById("passwordIn").value;
        let firstName = document.getElementById("firstNameIn").value;
        let lastName = document.getElementById("lastNameIn").value;
        console.log(`${username}, ${email}, ${password}, ${firstName},${lastName}`);
        signup(username,email,password,firstName,lastName);
    });

    document.getElementById("login").addEventListener("click", function(){

        login();
    });
});




//if coach or skier login then display their team and upcoming races make it simple
// if admin logs in we show a button for create season, create team, crete race, add coach, add skier