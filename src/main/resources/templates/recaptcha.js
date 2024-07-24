import {useGoogleReCaptcha} from "react-google-recaptcha-hook";

const FormComponent = () => {
    const {executeGoogleReCaptcha} = useGoogleReCaptcha(SITE_KEY);

    const submit = async () => {
        const token = await executeGoogleReCaptcha("ACTION_NAME");

        // Do whatever you want with the token
    };

    return <button onClick={submit}>SUBMIT</button>;
};