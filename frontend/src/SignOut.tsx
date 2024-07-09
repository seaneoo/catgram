import useSignOut from "react-auth-kit/hooks/useSignOut";

const SignOut = () => {
  const signOut = useSignOut();

  return (
    <div>
      <button onClick={() => signOut()}>Sign out</button>
    </div>
  );
};

export default SignOut;
