import useIsAuthenticated from "react-auth-kit/hooks/useIsAuthenticated";
import SignIn from "./SignIn";
import User from "./User";

export default function App() {
  const isAuthenticated = useIsAuthenticated();

  return (
    <div>
      <h1>Catgram</h1>
      {isAuthenticated ? <User /> : <SignIn />}
    </div>
  );
}
