import React, { useState } from "react";
import axios from "axios";

const LoginForm = () => {
    const [email, setEmail] = useState("eve.holt@reqres.in");
    const [password, setPassword] = useState("cityslicka");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();
        setMessage("");

        try {
            const response = await axios.post("https://reqres.in/api/login", {
                email,
                password,
            });

            const token = response.data.token;
            localStorage.setItem("authToken", token);
            setMessage("✅ Login successful!");
        } catch (error) {
            console.error(error);
            setMessage("❌ Login failed. Check credentials.");
        }
    };

    return (
        <div style={styles.container}>
            <h2>Login</h2>
            <form onSubmit={handleLogin} style={styles.form}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    style={styles.input}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    style={styles.input}
                />
                <button type="submit" style={styles.button}>Login</button>
            </form>
            <div>{message && <p>{message}</p>}</div>
        </div>
    );
};

const styles = {
    container: {
        maxWidth: "300px",
        margin: "100px auto",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "10px",
        textAlign: "center",
        fontFamily: "Arial",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "10px",
    },
    input: {
        padding: "8px",
        fontSize: "16px",
    },
    button: {
        padding: "10px",
        backgroundColor: "#4CAF50",
        color: "white",
        fontSize: "16px",
        cursor: "pointer",
    },
};

export default LoginForm;
