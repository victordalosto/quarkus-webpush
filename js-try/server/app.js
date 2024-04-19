const express = require("express")
const app = express()
const webpush = require('web-push')
const cors = require("cors")

const port = 3000

const apiKeys = {
    publicKey: "BNYguTJuTiyV4vpL1mC66U2B3gdu2c8TUViJ6MzwpXhEpe-j8mLW_NQgWBPRdOJzpgS1XI7C_biHirIA8ra8bQ4",
    privateKey: "Zts4BBcLE46UDiWsoGCtCmNUey6pdt7o_ExjXnn_TfE"
}

webpush.setVapidDetails(
    'mailto:victordalosto@gmail.com',
    apiKeys.publicKey,
    apiKeys.privateKey
)

app.use(cors())
app.use(express.json())

app.get("/", (req, res) => {
    res.send("Pagina inicial")
})

const subDatabse = []


app.post("/save-subscription", (req, res) => {
    console.log("headers: " + JSON.stringify(req.headers))
    console.log("body: " + JSON.stringify(req.body))
    subDatabse.push(req.body)
    res.json({ status: "Success", message: "Subscription saved!" })
})


app.get("/send-notification", async (req, res) => {
    var result = await webpush.sendNotification(subDatabse[0], "One new message has been pushed!!")
    console.log("Result: " + JSON.stringify(result))
    res.json({ "statue": "Success", "message": "Message sent to push service" })
})


app.listen(port, () => {
    console.log("Server running on port 3000!")
})