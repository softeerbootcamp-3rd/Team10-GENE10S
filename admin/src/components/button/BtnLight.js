import classNames from "classnames";

const BtnLight = ({ onClick, children }) => {
  return (
    <div className={classNames("btn-light")} onClick={onClick}>
      <span>{children}</span>
    </div>
  );
};

export default BtnLight;
