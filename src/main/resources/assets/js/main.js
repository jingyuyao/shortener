var CLIENT_ID = "850840293045-cjtgbesrdmnld2rmhrjaet2m90d5b4c4.apps.googleusercontent.com";

var signInButton = document.getElementById("sign-in-button");
var signOutButton = document.getElementById("sign-out-button");
var urlInput = document.getElementById("url-input");

/*
This is called when user clicks the button or when the user is logged in from before
*/
function onGoogleSignIn(googleUser) {
    console.log(googleUser);
}

function onGoogleSignInFailure(error) {
    console.log(error);
}

function googleSignOut() {
    gapi.auth2.getAuthInstance().signOut();
}

function googleLoginStateChange(signedIn) {
    if (signedIn) {
        signOutButton.className = "visible";
    } else {
        signOutButton.className = "hidden";
    }
}

function googleLoaded() {
    gapi.signin2.render('sign-in-button', {
            'scope': 'profile email',
            'width': 100,
            'height': 30,
            'longtitle': false,
            'theme': 'light',
            'onsuccess': onGoogleSignIn,
            'onfailure': onGoogleSignInFailure
         });

    gapi.load('auth2', function() {
        var auth2 = gapi.auth2.init({
            client_id: CLIENT_ID,
        });
        auth2.isSignedIn.listen(googleLoginStateChange);
    });
}

function urlInputKeyUp(event) {
    if (event.keyCode == 13) shorten();
}

function shorten() {
    var auth2 = gapi.auth2.getAuthInstance();
    var url = urlInput.value;

    if (auth2.isSignedIn.get()) {
        console.log("signed in create link");
    } else {
        console.log("anon create link");
    }
}