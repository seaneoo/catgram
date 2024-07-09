import axios from "axios";
import { useEffect, useState } from "react";
import useAuthHeader from "react-auth-kit/hooks/useAuthHeader";
import SignOut from "./SignOut";

type UserModel = {
  id: number;
  username: string;
  created_at: string;
};

const User = () => {
  const authHeader = useAuthHeader();
  const [user, setUser] = useState<UserModel>();

  useEffect(() => {
    axios
      .get("http://localhost:8080/v1/user/me", {
        headers: { Authorization: authHeader },
      })
      .then((res) => {
        if (res.status === 200) {
          setUser(res.data);
        }
      });
  }, []);

  return (
    <div>
      <pre>
        User: {user?.username}
        <br />
        ID: {user?.id}
        <br />
        Created: {user?.created_at}
      </pre>
      <SignOut />
    </div>
  );
};

export default User;
