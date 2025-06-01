document.addEventListener('DOMContentLoaded', function() {
    const apiUrl = 'http://localhost:8080/api/estudiantes';
    const studentsTable = document.getElementById('students-table').getElementsByTagName('tbody')[0];
    const studentIdInput = document.getElementById('student-id');
    const nombreInput = document.getElementById('nombre');
    const apellidoInput = document.getElementById('apellido');
    const emailInput = document.getElementById('email');
    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const formTitle = document.getElementById('form-title');

    let isEditing = false;
    let currentStudentId = null;

    // Cargar estudiantes al iniciar
    loadStudents();

    // Evento para guardar estudiante
    saveBtn.addEventListener('click', function() {
        const student = {
            nombre: nombreInput.value.trim(),
            apellido: apellidoInput.value.trim(),
            email: emailInput.value.trim()
        };

        if (!student.nombre || !student.apellido || !student.email) {
            alert('Por favor, completa todos los campos.');
            return;
        }

        if (isEditing) {
            updateStudent(currentStudentId, student);
        } else {
            createStudent(student);
        }
    });

    // Evento para cancelar edición
    cancelBtn.addEventListener('click', resetForm);

    // Función para cargar estudiantes
    function loadStudents() {
        fetch(apiUrl)
            .then(response => response.json())
            .then(students => {
                studentsTable.innerHTML = '';
                students.forEach(student => {
                    addStudentToTable(student);
                });
            })
            .catch(error => console.error('Error al cargar estudiantes:', error));
    }

    // Función para agregar estudiante a la tabla
    function addStudentToTable(student) {
        const row = studentsTable.insertRow();

        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.nombre}</td>
            <td>${student.apellido}</td>
            <td>${student.email}</td>
            <td class="actions">
                <button onclick="editStudent(${student.id})">Editar</button>
                <button onclick="deleteStudent(${student.id})">Eliminar</button>
            </td>
        `;
    }

    // Función para crear estudiante
    function createStudent(student) {
        fetch(`${apiUrl}/crea`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(student)
        })
        .then(response => response.json())
        .then(newStudent => {
            addStudentToTable(newStudent);
            resetForm();
        })
        .catch(error => console.error('Error al crear estudiante:', error));
    }

    // Función para actualizar estudiante
    function updateStudent(id, student) {
        fetch(`${apiUrl}/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(student)
        })
        .then(response => {
            if (response.ok) {
                loadStudents();
                resetForm();
            } else {
                console.error('Error al actualizar estudiante');
            }
        })
        .catch(error => console.error('Error al actualizar estudiante:', error));
    }

    // Función para preparar formulario para edición
    window.editStudent = function(id) {
        fetch(`${apiUrl}/${id}`)
            .then(response => response.json())
            .then(student => {
                isEditing = true;
                currentStudentId = student.id;

                studentIdInput.value = student.id;
                nombreInput.value = student.nombre;
                apellidoInput.value = student.apellido;
                emailInput.value = student.email;

                formTitle.textContent = 'Editar Estudiante';
                saveBtn.textContent = 'Actualizar';
                cancelBtn.style.display = 'inline-block';
            })
            .catch(error => console.error('Error al cargar estudiante para edición:', error));
    };

    // Función para eliminar estudiante
    window.deleteStudent = function(id) {
        if (confirm('¿Estás seguro de que deseas eliminar este estudiante?')) {
            fetch(`${apiUrl}/${id}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    loadStudents();
                } else {
                    console.error('Error al eliminar estudiante');
                }
            })
            .catch(error => console.error('Error al eliminar estudiante:', error));
        }
    };

    // Función para resetear el formulario
    function resetForm() {
        isEditing = false;
        currentStudentId = null;

        studentIdInput.value = '';
        nombreInput.value = '';
        apellidoInput.value = '';
        emailInput.value = '';

        formTitle.textContent = 'Agregar Nuevo Estudiante';
        saveBtn.textContent = 'Guardar';
        cancelBtn.style.display = 'none';
    }
});
