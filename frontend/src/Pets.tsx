import axios from "axios";
import { useEffect, useState } from "react";
import useAuthHeader from "react-auth-kit/hooks/useAuthHeader";

type PetModel = {
  id: number;
  name: string;
  species: string;
};

const Pets = () => {
  const authHeader = useAuthHeader();
  const [pets, setPets] = useState<Array<PetModel>>();

  useEffect(() => {
    axios
      .get("http://localhost:8080/v1/pets", {
        headers: { Authorization: authHeader },
      })
      .then((res) => {
        if (res.status === 200) {
          setPets(res.data);
        }
      });
  }, []);

  const _Pets = () => {
    return (
      <div>
        {pets?.map((p, i) => (
          <pre key={i}>
            Name: {p.name}
            <br />
            ID: {p.id}
            <br />
            Species: {p.species}
          </pre>
        ))}
      </div>
    );
  };

  return (
    <div>
      <h2>Pets</h2>
      {pets == undefined || pets!.length < 1 ? <p>No pets</p> : <_Pets />}
    </div>
  );
};

export default Pets;
