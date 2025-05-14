// Fetch and display all classes
function loadClasses() {
    fetch('/admin/api/classes')
        .then(res => res.json())
        .then(classes => {
            // Update your UI here, e.g., populate a table
            // Example: console.log(classes);
        });
}

// Add a new class
function addClass(name, instructor, schedule) {
    fetch('/admin/api/classes', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({name, instructor, schedule})
    }).then(() => loadClasses());
}

// Delete a class
function deleteClass(id) {
    fetch(`/admin/api/classes/${id}`, {method: 'DELETE'})
        .then(() => loadClasses());
}

// Call loadClasses() on page load
document.addEventListener('DOMContentLoaded', loadClasses);