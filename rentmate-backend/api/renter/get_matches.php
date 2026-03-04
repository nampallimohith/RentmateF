<?php
include_once '../../includes/db_connect.php';
include_once '../../includes/functions.php';
include_once '../../includes/jwt_helper.php';

// Auth Check
$headers = getallheaders();
$token = isset($headers['Authorization']) ? str_replace('Bearer ', '', $headers['Authorization']) : null;
$phone = validateJWT($token);

if (!$phone) {
    sendResponse("error", "Unauthorized access", null, 401);
}

// Fetch matches based on lifestyle preferences (Simplified Mock Logic)
// In production, join users with a preferences table and calculate scores
try {
    $stmt = $conn->prepare("SELECT id, name, occupation, gender, bio FROM users WHERE role = 'renter' AND phone != ? LIMIT 5");
    $stmt->execute([$phone]);
    $matches = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Add match percentage simulation
    foreach ($matches as &$match) {
        $match['match_percentage'] = rand(70, 98);
    }

    sendResponse("success", "Matches found", $matches);
} catch (Exception $e) {
    sendResponse("error", "Error fetching matches: " . $e->getMessage(), null, 500);
}
?>
