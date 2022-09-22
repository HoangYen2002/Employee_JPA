var selectedRow = null

function onFormSubmit() {
    if (validate()) {
        var formData = readFormData();
        if (selectedRow == null) {
        	 insertNewRecord(formData);
        	 addNewEmployee(formData);
        } else {
        	 updateRecord(formData);
        	 updateEmployee(formData);
        }
        resetForm();
    }
}

function validate() {
    isValid = true;
    if (document.getElementById("ssn").value == "") {//.value dùng để lấy ra nội dung của thẻ này
        isValid = false;
        document.getElementById("ssnValidationError").classList.remove("hide");//.classList dùng để truy cập đến các giá trị được khai báo trong thuộc tính class
    } else {
        isValid = true;
        if (!document.getElementById("ssnValidationError").classList.contains("hide"))
            document.getElementById("ssnValidationError").classList.add("hide");
    }
    return isValid;
}

function readFormData() {
    var formData = {};
    formData["ssn"] = document.getElementById("ssn").value;
    formData["name"] = document.getElementById("name").value;
    formData["salary"] = document.getElementById("salary").value;
    formData["department"] = document.getElementById("department").value;
    return formData;
}

function insertNewRecord(data) {
    var table = document.getElementById("employeeList").getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.length);
    cell1 = newRow.insertCell(0);
    cell1.innerHTML = data.ssn;
    cell2 = newRow.insertCell(1);
    cell2.innerHTML = data.name;
    cell3 = newRow.insertCell(2);
    cell3.innerHTML = data.salary;
    cell4 = newRow.insertCell(3);
    cell4.innerHTML = data.department;
    cell5 = newRow.insertCell(4);
    cell5.innerHTML = `<a onClick="onEdit(this)">Edit</a>
                       <a onClick="onDelete(this)">Delete</a>`;
}

function updateRecord(formData) {
    selectedRow.cells[0].innerHTML = formData.ssn;
    selectedRow.cells[1].innerHTML = formData.name;
    selectedRow.cells[2].innerHTML = formData.salary;
    selectedRow.cells[3].innerHTML = formData.department;
}

function resetForm() {
    document.getElementById("ssn").value = "";
    document.getElementById("name").value = "";
    document.getElementById("salary").value = "";
    document.getElementById("department").value = "";
    selectedRow = null;
}

function onEdit(td) {
    selectedRow = td.parentElement.parentElement;//parentElement để trả về thẻ cha của thẻ hiện tại
    document.getElementById("ssn").value = selectedRow.cells[0].innerHTML;
    document.getElementById("name").value = selectedRow.cells[1].innerHTML;
    document.getElementById("salary").value = selectedRow.cells[2].innerHTML;
    document.getElementById("department").value = selectedRow.cells[3].innerHTML;
}

function onDelete(td) {
    if (confirm('Are you sure to delete this record ?')) {//confirm() là hàm của Window Object(global object)
        row = td.parentElement.parentElement;// tr > td > a
        document.getElementById("employeeList").deleteRow(row.rowIndex);//.deleteRow(index) dùng để xóa một <tr>
        //deleteEmployee(row);
        resetForm();
    }
}

function getAllEmployees() {
	return currentPromise = new Promise(function(myResolve, myReject) {
		callAjaxGet('http://localhost:8080/springmvc/main/employee/list', myResolve);//thiếu http:// sẽ gây ra lỗi CORS
	});
}

function addNewEmployee(formData) {
	return currentPromise = new Promise(function(myResolve, myReject) {
		url = `http://localhost:8080/springmvc/main/employee/insert?ssn=${formData.ssn}&name=${formData.name}&salary=${formData.salary}&department=${formData.department}`
		callAjaxPost(url, formData, myResolve);//thiếu http:// sẽ gây ra lỗi CORS
	});
}

function addNewEmployee1(formData) {
	return currentPromise = new Promise(function(myResolve, myReject) {
		callAjaxPost1('http://localhost:8080/springmvc/main/employee/insert1', formData, myResolve);//thiếu http:// sẽ gây ra lỗi CORS
	});
}

function updateEmployee(formData) {
	url = `http://localhost:8080/springmvc/main/employee/update/${formData.ssn}?name=${formData.name}&salary=${formData.salary}&department=${formData.department}`
	return currentPromise = new Promise(function(myResolve, myReject) {
		callAjaxPost(url, formData, myResolve);//thiếu http:// sẽ gây ra lỗi CORS
	});
}

function deleteEmployee(formData) {
	return currentPromise = new Promise(function(myResolve, myReject) {
		url = `http://localhost:8080/springmvc/main/employee/delete/${formData.ssn}`
		callAjaxPost(url, formData, myResolve);//thiếu http:// sẽ gây ra lỗi CORS
	});
}

function myFunction() {
    return getAllEmployees()
        .then(function (data) {
        	console.log(data);
        	bind(data);
        })
        .catch(function(err) {
        	console.log(err);
        });
}

function callAjaxGet(url, myResolve) {
  const xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {//hàm này sẽ được gọi mỗi khi readyState thay đổi
    if (this.readyState == 4 && this.status == 200) {
    	myResolve(xhttp);
    }
  };
  xhttp.open("GET", url);
  xhttp.send();
}

function callAjaxPost(url, formData, myResolve) {
	  const xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			  myResolve(xhttp);
	    }
	  };
	  xhttp.open("POST", url);
	  xhttp.send();
}

function callAjaxPost1(url, formData, myResolve) {
	  const xhttp = new XMLHttpRequest();
	  xhttp.onreadystatechange = function() {
		  if (this.readyState == 4 && this.status == 200) {
	    	myResolve(xhttp);
	      }
	  };
	  xhttp.open("POST", url);

	  xhttp.setRequestHeader('key1', 'val1');
	  xhttp.setRequestHeader('key2', 'val2');
	  xhttp.setRequestHeader('Content-Type', 'application/json');

	  let post = JSON.stringify(formData)
	  xhttp.send(post);
}

function bind(data) {
	var table = document.getElementById("employeeList").getElementsByTagName('tbody')[0]; // lấy ra thẻ có id là employeeList và tiếp tục lấy ra thẻ tbody đầu tiên bên trong thẻ này
	employees = JSON.parse(data.response);//khi nhận dữ liệu từ backend trả về nó sẽ có dạng String -->phải chuyển thành dạng Javascript Object để có thể làm việc với nó
	employees.forEach(function myFunction(data, index) {
    	var newRow = table.insertRow(table.length);// thêm 1 <tr> vào <table> tại vị trí cuối cùng
	    cell1 = newRow.insertCell(0);// thêm 1 <td> vào <tr>
	    cell1.innerHTML = data.ssn;
	    cell2 = newRow.insertCell(1); // thêm 1 <td> vào <tr>
	    cell2.innerHTML = data.name;
	    cell3 = newRow.insertCell(2);
	    cell3.innerHTML = data.salary;
	    cell4 = newRow.insertCell(3);
	    cell4.innerHTML = data.department;
	    cell5 = newRow.insertCell(4);
	    cell5.innerHTML = `<a onClick="onEdit(this)">Edit</a>
                       <a onClick="onDelete(this)">Delete</a>`;
	});
}