import classNames from "classnames";

export default function BtnDark({ text, onClick }) {
  return (
    <div className={classNames("btn-dark")} onClick={onClick}>
      <span>{text}</span>
    </div>
  );
}
