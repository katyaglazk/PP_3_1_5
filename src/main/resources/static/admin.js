$(document).ready(function () {

    $.get('api/user', function (user) {
        $('#navbar-username').text(user.email);
    })
        .done(function () {
            console.log('Выполнено!');
        })
        .fail(function () {
            console.log('Ошибка запроса!');
        });

    let my_users = [];

    function updateUsersTable() {
        $.get('api/admin/users', function (users, httpStatus) {
            $.each(users, function (index, user) {
                my_users.push(user)
                let row = $("<tr />");
                $("<td id='td_id' />").html(user.id).appendTo(row);
                $("<td id='td_username' />").html(user.username).appendTo(row);
                $("<td id='td_name' />").html(user.name).appendTo(row);
                $("<td  id='td_surname'/>").html(user.surname).appendTo(row);
                $("<td id='td_age' />").html(user.age).appendTo(row);
                $("<td id='td_email' />").html(user.email).appendTo(row);
                $("<td id='td_role' />").html(user.rolesName).appendTo(row);

                const $cellEdit = $('<td></td>');
                const $cellDelete = $('<td></td>');

                const $buttonEdit = $('<button></button>')
                    .addClass('btn btn-info')
                    .text('Edit')
                    .attr({
                        "style": "color:white",
                        "id": "edit_button" + user.id,
                        "data-bs-toggle": "modal",
                        "data-bs-target": "#modalEdit",
                        "user-id": user.id
                    });
                $cellEdit.append($buttonEdit).appendTo(row);

                const $buttonDelete = $('<button></button>')
                    .addClass("btn btn-danger")
                    .text('Delete')
                    .attr({
                        "id": "delete_button" + user.id,
                        "data-bs-toggle": "modal",
                        "data-bs-target": "#modalDelete",
                        "user-id": user.id
                    });
                $cellDelete.append($buttonDelete).appendTo(row);

                row.appendTo($("#id-tbody-users"))
            });
        });
    }

    updateUsersTable();
    getAllRoles();
    console.log(my_users);
    let deleteUser;
    $(document).on('click', '.btn.btn-danger', function () {
        let $tr = $(this).closest('tr');
        let id = $tr.find("#td_id").text();
        let name = $tr.find("#td_name").text();
        let username = $tr.find("#td_username").text();
        let surname = $tr.find("#td_surname").text();
        let age = $tr.find("#td_age").text();
        let email = $tr.find("#td_email").text();
        let role = $tr.find("#td_rolesName").text();
        deleteUser = id;
        console.log("deleteUserId" + id);
        $('#modalDelete input[name="id_delete"]').val(id);
        $('#modalDelete input[name="username_delete"]').val(username);
        $('#modalDelete input[name="name_delete"]').val(name);
        $('#modalDelete input[name="surname_delete"]').val(surname);
        $('#modalDelete input[name="age_delete"]').val(age);
        $('#modalDelete input[name="email_delete"]').val(email);
        // $('#modalDelete input[name="role_delete"]').val(role);

        $('#modalDelete').attr(
            {
                "action": "api/admin/delete/" + id
            }
        )
    });

    let editUser
    let editId

    // при открытии
    async function getAllRoles() {
        let rolles = await fetch("http://localhost:8080/api/admin/roles").then(r => r.json());         //настройки для fetch можно указать после url
        let rol = "";
        rolles.forEach((r) => rol += `<option value="${r.id}">${r.name}</option>`);
        let sel = `<select name="sele" onchange="console.log($('#select').val())" id="select"
                            multiple class="form-select" size="2" aria-label="Default select">
                            ${rol}
                            </select>`;
        document.getElementById('create_role').innerHTML = sel;
    }

    // при изменении
    async function getRoles(idUser) {

        let rolles = await fetch("http://localhost:8080/api/admin/roles").then(r => r.json());

        let user = await fetch("http://localhost:8080/api/admin/user/" + idUser).then(r => r.json());
        let userRoles = user.roles;
        let rol = "";
        for (let n = 0; n < rolles.length; n++) {
            let isSelected = userRoles.some(role => role.name === rolles[n].name);
            console.log(isSelected);
            let selectedAttr = isSelected ? 'selected' : '';
            rol += `<option value="${rolles[n].id}" ${selectedAttr}> 
                ${rolles[n].name}</option>`;
        }
        let sel = `<select name="sele" onchange="console.log($('#selectEdit').val())" id="selectEdit"  
                multiple class="form-control sel" size="2">
                ${rol}
                </select>`;
        document.getElementById('selectorEdit').innerHTML = sel;
    }

    // открываем редактирование юзера
    $(document).on('click', '.btn.btn-info', function () {
        let $tr = $(this).closest('tr');
        let id = $tr.find("#td_id").text();
        let username = $tr.find("#td_username").text();
        let name = $tr.find("#td_name").text();
        let surname = $tr.find("#td_surname").text();
        let age = $tr.find("#td_age").text();
        let email = $tr.find("#td_email").text();
        let role = $tr.find("#td_role").text().split(', ');
        editId = id;

        getRoles(id);

        $('#modalEdit input[name="id_edit"]').val(id);
        $('#modalEdit input[name="username_edit"]').val(username);
        $('#modalEdit input[name="name_edit"]').val(name);
        $('#modalEdit input[name="surname_edit"]').val(surname);
        $('#modalEdit input[name="age_edit"]').val(age);
        $('#modalEdit input[name="email_edit"]').val(email);
        $('#modalEdit select[name="role_edit"] option').each(function () {
            if (role.includes($(this).text())) {
                $(this).attr('selected', 'selected');
            }
        });

        $('#modalEdit').attr(
            {
                "action": "api/admin/edit/" + id
            }
        )
    })

    $("#button_edit").on('click', function () {
        editUser = {
            'id': $('#modalEdit input[name="id_edit"]').val(),
            'name': $('#modalEdit input[name="name_edit"]').val(),
            'username': $('#modalEdit input[name="username_edit"]').val(),
            'surname': $('#modalEdit input[name="surname_edit"]').val(),
            'email': $('#modalEdit input[name="email_edit"]').val(),
            'age': $('#modalEdit input[name="age_edit"]').val(),
            'password': $('#modalEdit input[name="password_edit"]').val(),
            'roles': $('#modalEdit option:selected').map(function () {
                return {id: $(this).val(), role: $(this).text()};
            }).get()
        };

        $.ajax({
            type: 'PUT',
            url: 'api/admin/edit/' + editId,
            data: JSON.stringify(editUser),
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                $('.table.table-striped').find('#id-tbody-users').empty();
                updateUsersTable();
                console.log(response);
            },
            error: function (error) {
                console.log(error);
            }
        });


    });
    $("#button_delete").on('click', function () {
            $.ajax({
                type: 'DELETE',
                url: 'api/admin/delete/' + deleteUser,
                success: function (response) {
                    $('.table.table-striped').find('#id-tbody-users').empty();
                    updateUsersTable();
                    //$('.modal').hide();

                    console.log(response);
                },
                error: function (error) {
                    console.log(error);
                }
            });
        }
    );
    $.get('api/user/rolesString', function (rolesString) {
        $('#navbar-roles').text(rolesString)
    })
        .done(function () {
            //TODO
        })
        .fail(function () {
            //TODO
        });
    $('#form_create_user').submit(function (event) {
        event.preventDefault();
        const firstname = $('#create_firstname').val();
        const email = $('#create_email').val();
        const password = $('#create_password').val();
        const age = $('#create_age').val();
        const surname = $('#create_surname').val();
        const username = $('#create_username').val();
        const roles = $('#create_role option:selected').map(function () {
            return {id: $(this).val(), role: $(this).text()};
        }).get();

        let user = {
            'name': firstname,
            'surname': surname,
            'username': username,
            'email': email,
            'age': age,
            'password': password,
            'roles': roles,
        };
        $.ajax({
            type: 'POST',
            url: 'api/admin/create',
            data: JSON.stringify(user),
            dataType: 'json',
            contentType: 'application/json',
            success: function (response) {
                $('.table.table-striped').find('#id-tbody-users').empty();
                updateUsersTable();
                $('#nav-home-tab').tab('show');
                console.log(response);
            },
            error: function (error) {
                console.log(error);
            }
        });

    });
});