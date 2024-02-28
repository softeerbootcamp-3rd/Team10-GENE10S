import classNames from "classnames";
import BtnLight from "../button/BtnLight";
import { useNavigate } from "react-router-dom";

const TableRow = ({ data, headerInfo, link }) => {
  const navigate = useNavigate();

  const handleOnClick = (link) => {
    navigate(link);
  };

  return (
    <div
      className={classNames("tr", { link: link != null })}
      onClick={link != null ? () => handleOnClick(link) : null}
    >
      {headerInfo.map((info) => {
        if (data[info.name] === undefined) return null;
        return (
          <div
            key={`td-${data.key}-${info.name}`}
            className={classNames(`td w-${info.width}`)}
          >
            {data[info.name].type === "button" && (
              <BtnLight onClick={() => info.onClick(data.key)}>
                {data[info.name].text}
              </BtnLight>
            )}
            {data[info.name].type === "text" && (
              <span>{data[info.name].text}</span>
            )}
          </div>
        );
      })}
    </div>
  );
};

export default TableRow;
