let FetchUserUtils = {
    getAllUsers : function(param) {

var xhr = new XMLHttpRequest();
const baseUrl = "http://localhost:8080/resource";
xhr.open('GET', baseUrl + param);
xhr.onreadystatechange = function() {
  if (xhr.readyState === 4 && xhr.status === 200) {
    var response = JSON.parse(xhr.responseText);
    console.log(response);
    var tableHtml = '<table class="table table-bordered table-hover " >';
    tableHtml += '<thead><tr><th>ID</th><th>Fist Name</th><th>Last Name</th><th>Email</th><th>Profession</th><th>Country</th><th>City</th><th>Created Date</th></tr></thead>';
    tableHtml += '<tbody>';
    response.forEach(function(row) {
      tableHtml += '<tr><td>' + row.id + '</td><td>' + row.firstName + '</td><td>' + row.lastName + '</td><td>' + row.email + '</td><td>' + row.profession + '</td><td>' + row.country + '</td><td>' + row.city + '</td><td>' + row.dateCreated + '</td></tr>';
    });
    tableHtml += '</tbody></table>';
    document.getElementById('table-container').innerHTML = tableHtml;
  }
};
xhr.send();
},

fetchUserById : function(param) {

const resourceParam = "/id/"+param;
var xhr = new XMLHttpRequest();
const url = "http://localhost:8080/resource"+resourceParam;
xhr.open('GET', url);
    console.log(url);
xhr.onreadystatechange = function() {
  if (xhr.readyState === 4 && xhr.status === 200) {
    var response = JSON.parse(xhr.responseText);
    console.log(response);
    var tableHtml = '<table class="table table-bordered table-hover " >';
    tableHtml += '<thead><tr><th>ID</th><th>Fist Name</th><th>Last Name</th><th>Email</th><th>Profession</th><th>Country</th><th>City</th><th>Created Date</th></tr></thead>';
    tableHtml += '<tbody>';

     tableHtml += '<tr><td>' + response.id + '</td><td>' + response.firstName + '</td><td>' + response.lastName + '</td><td>' + response.email + '</td><td>' + response.profession + '</td><td>' + response.country + '</td><td>' + response.city + '</td><td>' + response.dateCreated + '</td></tr>';

     tableHtml += '</tbody></table>';
    document.getElementById('table-container').innerHTML = tableHtml;
  }
};
xhr.send();
},

fetchUsersByProfession : function(param) {
const resourceParam = "/profession/"+param;
FetchUserUtils.getAllUsers(resourceParam);
},

fetchUsersByDateRange : function(fromDate,toDate) {
const resourceParam = "/searchbycreateddate?fromDate="+fromDate+"&toDate="+toDate;
FetchUserUtils.getAllUsers(resourceParam)
},

}

const generalSubmitButton = document.getElementById("multipleSubButton");
const idTextField = document.getElementById("searchId");
const professionTextField = document.getElementById("searchProfession");
const emilTextField = document.getElementById("searchEmail");

// Add event listener to submit button
generalSubmitButton.addEventListener("click", function(event) {
    console.log("have beed called");
  // Prevent default form submission behavior
  event.preventDefault();

  // Get the value of the textfield
  const idFieldValue = idTextField.value;
  const professionFieldValue = professionTextField.value;
  const emailFieldValue = emilTextField.value;

  // Check if the textfield value is empty
  if (!(idFieldValue.trim() === "")) {
    FetchUserUtils.fetchUserById(idFieldValue.trim());
  } else if (!(professionFieldValue.trim() === "")){
    FetchUserUtils.fetchUsersByProfession(professionFieldValue.trim());
  }else if (!(emailFieldValue.trim() === "")){
    FetchUserUtils.fetchUserById(emailFieldValue.trim());
     }
});

const dateRangeSubButton = document.getElementById("dateRangeSubButton");
const fromTextField = document.getElementById("searchFrom");
const toTextField = document.getElementById("searchTo");
// Add event listener to submit button
dateRangeSubButton.addEventListener("click", function(event) {
  // Prevent default form submission behavior
  event.preventDefault();

  // Get the value of the textfield
  const fromFieldValue = fromTextField.value;
  const toFieldValue = toTextField.value;

   FetchUserUtils.fetchUsersByDateRange(fromFieldValue.trim(),toFieldValue);

});