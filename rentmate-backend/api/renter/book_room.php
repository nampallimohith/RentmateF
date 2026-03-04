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

$data = json_decode(file_get_contents("php://input"));

if (!isset($data->listing_id)) {
    sendResponse("error", "Listing ID is required", null, 400);
}

try {
    // 1. Get User ID
    $stmt = $conn->prepare("SELECT id FROM users WHERE phone = ?");
    $stmt->execute([$phone]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);

    // 2. Create Booking Entry (Status: Pending)
    $stmt = $conn->prepare("INSERT INTO bookings (user_id, listing_id, status, created_at) VALUES (?, ?, 'Pending', NOW())");
    $stmt->execute([$user['id'], $data->listing_id]);

    sendResponse("success", "Booking request submitted", ["booking_id" => $conn->lastInsertId()]);
} catch (Exception $e) {
    sendResponse("error", "Failed to submit booking: " . $e->getMessage(), null, 500);
}
?>
