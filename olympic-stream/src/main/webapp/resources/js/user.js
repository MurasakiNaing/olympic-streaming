function triggerFileInput() {
    document.getElementById('imageUpload').click();
}

var preview = document.getElementById('imagePreview');
var current = document.getElementById('currentImage');
var form = document.getElementById('pfp-form');

function previewImage(input) {
    var file = input.files[0];
    var reader = new FileReader();
  
    reader.onloadend = function () {
      preview.src = reader.result;
      current.classList.add('d-none');
      preview.classList.remove('d-none');
      form.classList.remove('d-none');
    };
  
    if (file) {
      reader.readAsDataURL(file);
    }
}

function discard() {
    preview.classList.add("d-none");
    current.classList.remove("d-none");
    form.classList.add('d-none');
}

function loadPasswordModal(condition) {
  if(condition) {
    document.getElementById('changePasswordBtn').click();
  }
}