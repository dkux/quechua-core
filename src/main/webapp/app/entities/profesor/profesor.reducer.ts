import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IProfesor, defaultValue } from 'app/shared/model/profesor.model';

export const ACTION_TYPES = {
  FETCH_PROFESOR_LIST: 'profesor/FETCH_PROFESOR_LIST',
  FETCH_PROFESOR: 'profesor/FETCH_PROFESOR',
  CREATE_PROFESOR: 'profesor/CREATE_PROFESOR',
  UPDATE_PROFESOR: 'profesor/UPDATE_PROFESOR',
  DELETE_PROFESOR: 'profesor/DELETE_PROFESOR',
  RESET: 'profesor/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IProfesor>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ProfesorState = Readonly<typeof initialState>;

// Reducer

export default (state: ProfesorState = initialState, action): ProfesorState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PROFESOR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PROFESOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PROFESOR):
    case REQUEST(ACTION_TYPES.UPDATE_PROFESOR):
    case REQUEST(ACTION_TYPES.DELETE_PROFESOR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PROFESOR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PROFESOR):
    case FAILURE(ACTION_TYPES.CREATE_PROFESOR):
    case FAILURE(ACTION_TYPES.UPDATE_PROFESOR):
    case FAILURE(ACTION_TYPES.DELETE_PROFESOR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROFESOR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PROFESOR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PROFESOR):
    case SUCCESS(ACTION_TYPES.UPDATE_PROFESOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PROFESOR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/profesors';

// Actions

export const getEntities: ICrudGetAllAction<IProfesor> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PROFESOR_LIST,
  payload: axios.get<IProfesor>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IProfesor> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PROFESOR,
    payload: axios.get<IProfesor>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IProfesor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PROFESOR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IProfesor> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PROFESOR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IProfesor> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PROFESOR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
