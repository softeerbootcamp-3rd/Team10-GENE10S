import classNames from "classnames";

const InfoSteps = ({ label, children }) => {
  return (
    <>
      <div className={classNames("td", "header")}>
        <span>{label}</span>
      </div>
      <div className={classNames("td", "reservation-steps")}>{children}</div>
    </>
  );
};

export default InfoSteps;
