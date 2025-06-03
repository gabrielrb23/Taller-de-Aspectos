document.addEventListener('DOMContentLoaded', () => {
    const apiUrl = 'http://localhost:8080/api/estudiantes';
    const studentsTable = document.querySelector('#students-table tbody');
    const formTitle = document.querySelector('#form-title');
    const saveBtn = document.querySelector('#save-btn');
    const cancelBtn = document.querySelector('#cancel-btn');

    // Referenciar directamente los inputs del formulario
    const studentIdInput = document.querySelector('#student-id');
    const nombreInput = document.querySelector('#nombre');
    const apellidoInput = document.querySelector('#apellido');
    const emailInput = document.querySelector('#email');

    let isEditing = false;
    let currentStudentId = null;

    // Cargar estudiantes al iniciar
    const loadStudents = async () => {
        try {
            const response = await fetch(apiUrl);
            const students = await response.json();
            studentsTable.innerHTML = '';
            students.forEach(student => addStudentToTable(student));
        } catch (error) {
            console.error('Error al cargar estudiantes:', error);
        }
    };

    const addStudentToTable = (student) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${student.id}</td>
            <td>${student.nombre}</td>
            <td>${student.apellido}</td>
            <td>${student.correo}</td>
            <td>
                <button class="edit-btn" data-id="${student.id}">Editar</button>
                <button class="delete-btn" data-id="${student.id}">Eliminar</button>
            </td>
        `;
        studentsTable.appendChild(row);
    };

    const resetForm = () => {
        studentIdInput.value = '';
        nombreInput.value = '';
        apellidoInput.value = '';
        emailInput.value = '';

        formTitle.textContent = 'Agregar Nuevo Estudiante';
        saveBtn.textContent = 'Guardar';
        cancelBtn.style.display = 'none';
        isEditing = false;
        currentStudentId = null;
    };

    const saveStudent = async (event) => {
        event.preventDefault();

        const student = {
            nombre: nombreInput.value.trim(),
            apellido: apellidoInput.value.trim(),
            correo: emailInput.value.trim()
        };

        if (!student.nombre || !student.apellido || !student.correo) {
            alert('Por favor, completa todos los campos.');
            return;
        }

        // Validación adicional para el correo electrónico
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(student.correo)) {
            alert('Por favor, ingresa un correo electrónico válido.');
            return;
        }

        try {
            if (isEditing) {
                const response = await fetch(`${apiUrl}/${currentStudentId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(student)
                });
                if (response.status === 409) {
                    alert('El correo electrónico ya está registrado.');
                    return;
                }
            } else {
                const response = await fetch(`${apiUrl}/crea`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(student)
                });
                if (response.status === 409) {
                    alert('El correo electrónico ya está registrado.');
                    return;
                }
            }
            loadStudents();
            resetForm();
        } catch (error) {
            console.error('Error al guardar estudiante:', error);
            alert('Ocurrió un error al guardar el estudiante.');
        }
    };

    const editStudent = async (id) => {
        try {
            const response = await fetch(`${apiUrl}/${id}`);
            const student = await response.json();

            studentIdInput.value = student.id || '';
            nombreInput.value = student.nombre || '';
            apellidoInput.value = student.apellido || '';
            emailInput.value = student.correo || '';

            formTitle.textContent = 'Editar Estudiante';
            saveBtn.textContent = 'Actualizar';
            cancelBtn.style.display = 'inline-block';

            isEditing = true;
            currentStudentId = id;
        } catch (error) {
            console.error('Error al cargar estudiante para edición:', error);
        }
    };

    const deleteStudent = async (id) => {
        if (confirm('¿Estás seguro de que deseas eliminar este estudiante?')) {
            try {
                await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
                loadStudents();
            } catch (error) {
                console.error('Error al eliminar estudiante:', error);
            }
        }
    };

    studentsTable.addEventListener('click', (event) => {
        const target = event.target;
        if (target.classList.contains('edit-btn')) {
            editStudent(target.dataset.id);
        } else if (target.classList.contains('delete-btn')) {
            deleteStudent(target.dataset.id);
        }
    });

    saveBtn.addEventListener('click', saveStudent);
    cancelBtn.addEventListener('click', resetForm);

    loadStudents();
});
