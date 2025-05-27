document.addEventListener('DOMContentLoaded', () => {
    const loadMembersBtn = document.getElementById('loadMembersBtn');
    const sortMembersBtn = document.getElementById('sortMembersBtn');
    const memberListUl = document.getElementById('memberList');
    const logListUl = document.getElementById('logList');

    let membersData = []; // To store loaded member data
    const uiUpdateQueue = []; // Queue for demonstrating sequential UI updates

    // --- Data Loading ---
    async function fetchMembers() {
        // In a real app, this would be an API call.
        // Here, we simulate by fetching a local JSON file.
        // This path is relative to where index.html is served.
        // For local file access, you might need to run a simple local server
        // or adjust browser security settings if you encounter issues.
        // For this demo, we'll assume it works or we'll manually copy members.json content.
        const filePath = '../backend/data/members.json'; // Path relative to index.html
        logMessage(`Attempting to load members from: ${filePath}`);
        try {
            const response = await fetch(filePath);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status} - Could not load ${filePath}. Make sure the backend/data/members.json file exists at the correct relative path or try running a local server.`);
            }
            membersData = await response.json();
            logMessage(`Successfully loaded ${membersData.length} members.`);
            enqueueUiUpdates(membersData, 'display'); // Use queue to display
        } catch (error) {
            console.error('Error fetching members:', error);
            logMessage(`Error loading members: ${error.message}. Displaying sample data instead.`);
            // Fallback to sample data if fetch fails (e.g., due to file path issues without a server)
            membersData = [
                { "id": "M001", "name": "Alice Smith (Sample)", "renewalDue": "2024-03-01" },
                { "id": "M002", "name": "Bob Johnson (Sample)", "renewalDue": "2023-12-15" },
                { "id": "M003", "name": "Carol White (Sample)", "renewalDue": "2024-01-20" }
            ];
            enqueueUiUpdates(membersData, 'display');
        }
    }

    // --- UI Update Queue Sketch ---
    function logMessage(message) {
        const li = document.createElement('li');
        li.textContent = `[${new Date().toLocaleTimeString()}] ${message}`;
        logListUl.appendChild(li);
    }

    function enqueueUiUpdates(items, action) {
        logMessage(`Queueing ${items.length} items for ${action}.`);
        items.forEach(item => uiUpdateQueue.push({ item, action }));
        processUiUpdateQueue();
    }

    let isProcessingQueue = false;
    function processUiUpdateQueue() {
        if (isProcessingQueue || uiUpdateQueue.length === 0) {
            if (uiUpdateQueue.length === 0 && isProcessingQueue) {
                logMessage("UI Update Queue processing complete.");
                isProcessingQueue = false;
            }
            return;
        }
        isProcessingQueue = true;
        logMessage("Starting UI Update Queue processing...");

        const { item, action } = uiUpdateQueue.shift(); // Dequeue
        logMessage(`Processing item ID ${item.id} for action: ${action}`);

        if (action === 'display') {
            displayMember(item);
        }
        // Add other actions here if needed

        // Simulate async processing / give browser time to render
        setTimeout(() => {
            isProcessingQueue = false; // Allow next item to be processed
            processUiUpdateQueue(); // Process next item
        }, 500); // 0.5 second delay for demo
    }


    // --- Member Display ---
    function displayMember(member) {
        const li = document.createElement('li');
        li.textContent = `ID: ${member.id}, Name: ${member.name}, Renewal Due: ${member.renewalDue}`;
        memberListUl.appendChild(li);
    }

    function renderMembers(members) {
        memberListUl.innerHTML = ''; // Clear existing list
        members.forEach(member => displayMember(member)); // Display directly for now after sorting
        // For queued display, you'd call enqueueUiUpdates(members, 'display');
    }


    // --- Insertion Sort ---
    function insertionSortMembers(array) {
        if (!array || array.length <= 1) return array;

        for (let i = 1; i < array.length; i++) {
            let key = array[i];
            let j = i - 1;
            while (j >= 0 && array[j].renewalDue.localeCompare(key.renewalDue) > 0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
        return array;
    }

    // --- Event Listeners ---
    loadMembersBtn.addEventListener('click', () => {
        memberListUl.innerHTML = ''; // Clear current list before loading
        logListUl.innerHTML = ''; // Clear log
        fetchMembers();
    });

    sortMembersBtn.addEventListener('click', () => {
        if (membersData.length === 0) {
            logMessage("No members loaded to sort.");
            alert("Please load members first.");
            return;
        }
        logMessage("Sorting members by renewalDue date.");
        insertionSortMembers(membersData);
        logMessage("Sorting complete. Re-rendering members.");
        renderMembers(membersData); // Re-render the sorted list directly
    });

    logMessage("Frontend initialized. Click 'Load Members' to start.");
});
