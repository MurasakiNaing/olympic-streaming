var adminForm = document.getElementById("adminLogin");
var userForm = document.getElementById("userLogin");
var userTag = document.getElementById("userTag");
var adminTag = document.getElementById("adminTag");

adminTag.addEventListener('click', () => {
	document.title = 'Admin Login';
	adminForm.classList.remove('d-none');
	userForm.classList.add('d-none');
})

userTag.addEventListener('click', ()=> {
	document.title = 'User Login';
	adminForm.classList.add('d-none');
	userForm.classList.remove('d-none');
})