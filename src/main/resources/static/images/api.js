// api.js

// Exercise API calls
const exerciseApi = {
    getAllExercises: async function() {
        const response = await fetch('/api/exercises');
        return response.json();
    },

    getExerciseById: async function(id) {
        const response = await fetch(`/api/exercises/${id}`);
        return response.json();
    },

    getExercisesByCategory: async function(category) {
        const response = await fetch(`/api/exercises/category/${category}`);
        return response.json();
    },

    createExercise: async function(exercise) {
        const response = await fetch('/api/exercises', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(exercise),
        });
        return response.json();
    },

    updateExercise: async function(id, exercise) {
        const response = await fetch(`/api/exercises/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(exercise),
        });
        return response.json();
    },

    deleteExercise: async function(id) {
        const response = await fetch(`/api/exercises/${id}`, {
            method: 'DELETE',
        });
        return response.status === 204;
    },
};

// Progress API calls
const progressApi = {
    getAllProgress: async function() {
        const response = await fetch('/api/progress');
        return response.json();
    },

    getProgressById: async function(id) {
        const response = await fetch(`/api/progress/${id}`);
        return response.json();
    },

    getProgressByUserId: async function(userId) {
        const response = await fetch(`/api/progress/user/${userId}`);
        return response.json();
    },

    createProgress: async function(progress) {
        const response = await fetch('/api/progress', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(progress),
        });
        return response.json();
    },

    updateProgress: async function(id, progress) {
        const response = await fetch(`/api/progress/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(progress),
        });
        return response.json();
    },

    deleteProgress: async function(id) {
        const response = await fetch(`/api/progress/${id}`, {
            method: 'DELETE',
        });
        return response.status === 204;
    },
};

// Schedule API calls
const scheduleApi = {
    getAllSchedules: async function() {
        const response = await fetch('/api/schedules');
        return response.json();
    },

    getScheduleById: async function(id) {
        const response = await fetch(`/api/schedules/${id}`);
        return response.json();
    },

    getSchedulesByUserId: async function(userId) {
        const response = await fetch(`/api/schedules/user/${userId}`);
        return response.json();
    },

    getScheduleByUserIdAndDate: async function(userId, date) {
        const response = await fetch(`/api/schedules/user/${userId}/date/${date}`);
        return response.json();
    },

    createSchedule: async function(schedule) {
        const response = await fetch('/api/schedules', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(schedule),
        });
        return response.json();
    },

    updateSchedule: async function(id, schedule) {
        const response = await fetch(`/api/schedules/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(schedule),
        });
        return response.json();
    },

    updateWorkoutStatus: async function(scheduleId, workoutId, status) {
        const response = await fetch(`/api/schedules/${scheduleId}/workouts/${workoutId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ status }),
        });
        return response.json();
    },

    deleteSchedule: async function(id) {
        const response = await fetch(`/api/schedules/${id}`, {
            method: 'DELETE',
        });
        return response.status === 204;
    },
};