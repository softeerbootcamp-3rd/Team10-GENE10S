import classNames from "classnames";

const BtnDark = ({ onClick, children }) => {
  return (
    <div className={classNames("btn-dark")} onClick={onClick}>
      <span>{children}</span>
    </div>
  );
};

export default BtnDark;
