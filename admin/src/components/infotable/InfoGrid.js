import classNames from "classnames";

const InfoGrid = ({ label, children }) => {
  return (
    <>
      <div className={classNames("td", "header")}>
        <span>{label}</span>
      </div>
      <div className={classNames("td", "grid")}>{children}</div>
    </>
  );
};

export default InfoGrid;
