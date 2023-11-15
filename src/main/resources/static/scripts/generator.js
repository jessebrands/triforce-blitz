/**
 * This script implements a STOMP-based websocket client for the Triforce Blitz generator application.
 *
 * While most of the site uses HTMX for interactivity and prefers a strict HATEOAS model, this is unfortunately not
 * particularly feasible for the generator. The generator service is a little too complex to do with just HTML
 * responses.
 */

const log = document.getElementById("generator-log");

function logMessage(message) {
    const el = document.createElement("div");
    el.innerText = message;
    el.className = "generator-log__message"
    log.appendChild(el);
}

const client = new StompJs.Client({
    brokerURL: "ws://" + location.host + "/ws",
    onConnect: () => {
        client.subscribe("/user/topic/generator/status", frame => {
            const {message} = JSON.parse(frame.body);
            logMessage(message);
        });

        logMessage("Connected to Triforce Blitz Generator Service");
    },
});

function generateSeed(version, season) {
    if (client) {
        client.publish({
            destination: "/app/generator/generate",
            body: JSON.stringify({
                version: version,
                season: season,
            }),
        });
    }
}

client.activate();

// Attach handlers to our HTML.
document.getElementById("generator-submit").onclick = (event) => {
    if (client.connected) {
        const season = document.querySelector("input[name='season']:checked").value;
        const version = document.getElementById("generator-version").value;
        generateSeed(version, season);
        // Prevent submitting through HTTP.
        event.preventDefault();
    }
}
