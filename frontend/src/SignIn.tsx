import axios from "axios";
import { FormEvent, useRef, useState } from "react";
import useSignIn from "react-auth-kit/hooks/useSignIn";

const SignIn = () => {
  const signIn = useSignIn();
  const [formData, setFormData] = useState({ username: "", password: "" });
  const form = useRef<HTMLFormElement>(null);

  const onSubmit = (e: FormEvent) => {
    e.preventDefault();

    axios.post("http://localhost:8080/v1/auth/login", formData).then((res) => {
      if (res.status === 200) {
        signIn({
          auth: {
            token: res.data.token,
            type: "Bearer",
          },
          userState: { username: formData.username },
        });

        form.current?.reset();
      }
    });
  };

  return (
    <form onSubmit={onSubmit} ref={form}>
      <input
        type="text"
        onChange={(e) => setFormData({ ...formData, username: e.target.value })}
      />
      <input
        type="password"
        onChange={(e) => setFormData({ ...formData, password: e.target.value })}
      />
      <button>Submit</button>
    </form>
  );
};

export default SignIn;
