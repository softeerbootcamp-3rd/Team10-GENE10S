import classNames from 'classnames'

const BtnLight = ({ text, onClick }) => {
  return (
    <div className={classNames('btn-light')} onClick={onClick}>
      <span>{text}</span>
    </div>
  )
}

export default BtnLight
