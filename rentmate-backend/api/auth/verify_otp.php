<?php
include_once '../../includes/db_connect.php';
include_once '../../includes/functions.php';
include_once '../../includes/jwt_helper.php';

$data = json_decode(file_get_contents("php://input"));

if (!validateInput($data, ['phone', 'otp'])) {
    sendResponse("error", "Phone and OTP are required", null, 400);
}

// 1. Verify OTP in DB
$stmt = $conn->prepare("SELECT * FROM otps WHERE phone = ? AND code = ? AND expiry > NOW()");
$stmt->execute([$data->phone, $data->otp]);
$otp_record = $stmt->fetch(PDO::FETCH_ASSOC);

if (!$otp_record) {
    sendResponse("error", "Invalid or expired OTP", null, 401);
}

// 2. Fetch User Role for the app
$stmt = $conn->prepare("SELECT role FROM users WHERE phone = ?");
$stmt->execute([$data->phone]);
$user = $stmt->fetch(PDO::FETCH_ASSOC);

// 3. Delete OTP after successful verification
$stmt = $conn->prepare("DELETE FROM otps WHERE phone = ?");
$stmt->execute([$data->phone]);

// 4. Generate Session Token
$token = generateJWT($data->phone);

sendResponse("success", "OTP verified successfully", [
    "token" => $token,
    "role" => $user['role'],
    "phone" => $data->phone
]);
?>
