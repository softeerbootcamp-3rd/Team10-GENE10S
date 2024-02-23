import classNames from 'classnames'

const BtnDark = ({ text, onClick }) => {
  return (
    <div className={classNames('btn-dark')} onClick={onClick}>
      <span>{text}</span>
    </div>
  )
}

export default BtnDark
